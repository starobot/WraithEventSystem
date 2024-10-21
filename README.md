# Staro Event System

An EXTREMELY FAST java event system. This is the continuation of the @ARZI1 event system <br>
https://github.com/ARZI1/eventsystem

Here are the benchmark comparison between StaroEventSystem, Orbit and Energy and Pingbypass Event Bus upon posting one million events: <br>
Staro: 15 ms <br>
Orbit: 44 ms <br>
Energy: 85 ms <br>
Pingbypass: 28 ms <br>
https://github.com/MeteorDevelopment/orbit <br>
https://github.com/QuantumClient/Energy <br>
https://github.com/3arthqu4ke/pingbypass <br>

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
    implementation 'com.github.starobot:WraithEventSystem:420.69'
}
```

### How to override the EventBus and create more generic listeners
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
