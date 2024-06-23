package biden.give.bombs.to.bomb.donetsk.children;

import biden.give.bombs.to.bomb.donetsk.children.annotation.EventListener;
import biden.give.bombs.to.bomb.donetsk.children.annotation.SafeEventListener;
import biden.give.bombs.to.bomb.donetsk.children.listener.Listener;
import biden.give.bombs.to.bomb.donetsk.children.listener.SafeListener;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;

/**
 * The event bus for registering and distributing events.
 * @see Listener
 */
public class EventBus
{

    /**
     * A hashmap is used for fast event lookup. Listeners are put into a list based on their event class.
     */
    private final HashMap<Class<?>, LinkedList<Listener>> listeners;

    /**
     * Weak references subscribed objects to the event types they are listening to.
     * This allows an automatic unsubscription when the object is garbage collected.
     */
    private final WeakHashMap<Object, List<Class<?>>> subscriptions;

    /**
     * Stores factory functions for creating listeners based on their annotations.
     */
    private final Map<Class<? extends Annotation>, BiFunction<Object, Method, Listener>> listenerFactories = new HashMap<>();

    public EventBus()
    {
        this.listeners = new HashMap<>();
        this.subscriptions = new WeakHashMap<>();
        registerListenerFactory(EventListener.class, (instance, method) ->
                new Listener(instance, method, method.getAnnotation(EventListener.class).priority().getVal())
        );
        registerListenerFactory(SafeEventListener.class, (instance, method) ->
                new SafeListener(instance, method, method.getAnnotation(SafeEventListener.class).priority().getVal())
        );
    }

    /**
     * Registers a factory function for creating a specific type of listener based on its annotation.
     *
     * @param annotationType The annotation class associated with the listener type.
     * @param factory The function that creates the listener instance given the object and method.
     */
    public void registerListenerFactory(Class<? extends Annotation> annotationType, BiFunction<Object, Method, Listener> factory)
    {
        listenerFactories.put(annotationType, factory);
    }

    /**
     * Posts an event to all registered listeners.
     * @param event The event object.
     */
    public void post(Object event)
    {
        removeStaleListeners();
        if (!listeners.containsKey(event.getClass()))
        {
            return;
        }

        for (Listener l : listeners.get(event.getClass()))
        {
            if (l.getInstance() != null)
            {
                Class<?> eventParamType = l.getMethod().getParameterTypes()[0];
                if (eventParamType.isAssignableFrom(event.getClass()))
                {
                    l.invoke(event);
                }
            }
        }
    }

    /**
     * Subscribes listeners.
     * @param instance is a listener.
     */
    public void subscribe(Object instance)
    {
        addListeners(getListeningMethods(instance.getClass()), instance);
    }

    /**
     * Unsubscribes listeners.
     * @param instance is a listener
     */
    public void unsubscribe(Object instance)
    {
        removeListeners(getListeningMethods(instance.getClass()), instance);
        subscriptions.remove(instance);
    }

    /**
     * Turns methods we know are listeners into listener objects.
     *
     * @param methods  the methods we want to turn into listeners
     * @param instance the method's class' instance (null if methods are static)
     */
    private void addListeners(List<Method> methods, Object instance)
    {
        List<Class<?>> subscribedEvents = subscriptions.computeIfAbsent(instance, k -> new ArrayList<>());

        for (Method method : methods)
        {
            Class<?> eventType = getEventParameterType(method);
            listeners.putIfAbsent(eventType, new LinkedList<>());
            LinkedList<Listener> list = listeners.get(eventType);

            for (Annotation annotation : method.getAnnotations())
            {
                if (listenerFactories.containsKey(annotation.annotationType()))
                {
                    BiFunction<Object, Method, Listener> factory = listenerFactories.get(annotation.annotationType());
                    Listener listener = factory.apply(instance, method);

                    int index = getPriorityIndex(list, listener.getPriority());

                    list.add(index, listener);
                    subscribedEvents.add(eventType);
                    break;
                }
            }
        }
    }

    private int getPriorityIndex(LinkedList<Listener> list, int priority)
    {
        int index = list.size();
        Iterator<Listener> iterator = list.descendingIterator();
        while (iterator.hasNext())
        {
            if (iterator.next().getPriority() > priority)
            {
                break;
            } else
            {
                index--;
            }
        }
        return index;
    }

    /**
     * Removes Listeners by looping over their respective lists
     *
     * @param methods  the methods we want to remove
     * @param instance method's class' instance (null if methods are static)
     */
    private void removeListeners(List<Method> methods, Object instance)
    {
        for (Method method : methods)
        {
            Class<?> eventType = getEventParameterType(method);
            LinkedList<Listener> list = listeners.get(eventType);
            if (list == null)
            {
                continue;
            }

            list.removeIf(l -> l.getMethod().equals(method) && l.getInstance() == instance);
        }
    }

    private List<Method> getListeningMethods(Class<?> clazz)
    {
        ArrayList<Method> listening = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods())
        {
            for (Annotation annotation : method.getDeclaredAnnotations())
            {
                if (listenerFactories.containsKey(annotation.annotationType()) && method.getParameterCount() == 1)
                {
                    listening.add(method);
                }
            }
        }
        return listening;
    }

    private static Class<?> getEventParameterType(Method method)
    {
        if (method.getParameterCount() != 1)
        {
            return null;
        }

        return method.getParameters()[0].getType();
    }

    /**
     * Removes the garbage collected listeners.
     */
    private void removeStaleListeners()
    {
        for (Class<?> eventType : listeners.keySet())
        {
            LinkedList<Listener> list = listeners.get(eventType);
            list.removeIf(l -> l.getInstance() == null);
        }
    }
}
