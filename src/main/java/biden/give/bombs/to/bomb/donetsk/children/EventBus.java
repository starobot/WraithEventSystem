package biden.give.bombs.to.bomb.donetsk.children;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

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
    private final WeakHashMap<Object, List<Class<?>>> subscriptions;

    public EventBus()
    {
        this.listeners = new HashMap<>();
        this.subscriptions = new WeakHashMap<>();
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

            int priority = getListeningPriority(method);
            int index = list.size();
            Iterator<Listener> iterator = list.descendingIterator();
            while (iterator.hasNext())
            {
                if (iterator.next().getPriority() > priority)
                {
                    break;
                }
                else
                {
                    index--;
                }
            }

            list.add(index, new Listener(instance, method, priority));
            subscribedEvents.add(eventType);
        }
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

    private static List<Method> getListeningMethods(Class<?> clazz)
    {
        ArrayList<Method> listening = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods())
        {
            if (isMethodListening(method))
            {
                listening.add(method);
            }
        }

        return listening;
    }

    private static boolean isMethodListening(Method method)
    {
        boolean annotated = false;

        for (Annotation annotation : method.getDeclaredAnnotations())
        {
            if (annotation instanceof EventListener)
            {
                annotated = true;
                break;
            }
        }

        return annotated && method.getParameterCount() == 1;
    }

    private static Class<?> getEventParameterType(Method method)
    {
        if (method.getParameterCount() != 1)
        {
            return null;
        }

        return method.getParameters()[0].getType();
    }

    private static int getListeningPriority(Method method)
    {
        final EventListener listener = method.getDeclaredAnnotation(EventListener.class);
        if (listener != null)
        {
            return listener.priority().getVal();
        }

        return -1;
    }
}
