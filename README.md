# Wraith Event System

A simple java event system aimed for minecraft mod development. This is the continuation of the @ARZI1 event system
https://github.com/ARZI1/eventsystem

Originally made for Wraith client, hovewer I decided to make it public due to my github profile being empty.

### How to use the subscription and posting:
```java
EventBus eventBus = new EventBus();

// to post the custom event
eventBus.post(new Event());

// to subscribe the current instance
eventBus.subscribe(this);
eventBus.subsribe(new TickListener);
```

### How to create listeners:
```java
@Listener
public void invoke(Event event) // the method must be public
{
  event.getSomething();
}
```
# Library implementation
```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```
```
dependencies {
    implementation 'com.github.starobot:WraithEventSystem:1.5'
}
```

## How to work with the API:
Override the GenericListener or implement an EventListener interface to your custom generic listener.
```java
public class CustomGenericListener extends GenericListener
{
    public CustomGenericListener(Object instance, Method method, int priority)
    {
        super(instance, method, priority);
    }

    @Override
    public void invoke(Object event)
    {
        // add the additional functionality before using the super.invoke(Object event) method.
    }
}
```
Create annotation for the CustomGenericListener
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomListener
{
    Priority priority() default Priority.DEFAULT;
}
```
Then override EventBus to register the new generic listener with it's annotation.
```java
public class CustomEventBus extends EventBus
{
    public CustomEventBus()
    {
        super.registerListenerFactory(CustomListener.class, (instance, method) ->
                new CustomGenericListener(instance, method, method.getAnnotation(CustomListener.class).priority().getVal())
        );
    }
}
```
Now you are golden:
```java
CustomEventBus eventBus = new CustomEventBus();
```
