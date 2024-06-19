# Wraith Event System

A simple java event system aimed for minecraft mod development. This is the continuation of the @ARZI1 event system
https://github.com/ARZI1/eventsystem

Originally made for Wraith client, hovewer I decided to make it public due to my github profile being empty.

### How to use the subscription and posting:
```java
EventBus eventBus = new EventBus;

// to post the custom event
eventBus.post(new Event());

// to subscribe the current instance
eventBus.subscribe(this);
```

### How to create listeners:
```java
@EventListener
public void invoke(Event event) // the method must be public
{
  event.getSomething();
}
```
