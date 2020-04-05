# Database Handling
AbilityBots come with an embedded DB. Users are free to implement their own databases via implementing the [DBContext](../../telegrambots-abilities/src/main/java/org/telegram/abilitybots/api/db/DBContext.java) class.
The abstraction has multiple constructors to accommodate user-defined implementations of [DBContext](../../telegrambots-abilities/src/main/java/org/telegram/abilitybots/api/db/DBContext.java) and [MessageSender](../../telegrambots-abilities/src/main/java/org/telegram/abilitybots/api/sender/MessageSender.java). We'll talk about the message sender interface in the [[Bot Testing|Bot-Testing]] section.

## Example
We'll be introducing an ability that maintains a special counter for every user. At every /increment, the user will receive a message with the previous number + 1. We'll initially start from zero and increment upwards.

```java
 /**
   * Use the database to fetch a count per user and increments.
   * <p>
   * Use /count to experiment with this ability.
   */
  public Ability useDatabaseToCountPerUser() {
    return Ability.builder()
        .name("count")
        .info("Increments a counter per user")
        .privacy(PUBLIC)
        .locality(ALL)
        .input(0)
        .action(ctx -> {
          // db.getMap takes in a string, this must be unique and the same everytime you want to call the exact same map
          // TODO: Using integer as a key in this db map is not recommended, it won't be serialized/deserialized properly if you ever decide to recover/backup db
          Map<String, Integer> countMap = db.getMap("COUNTERS");
          int userId = ctx.user().getId();

          // Get and increment counter, put it back in the map
          Integer counter = countMap.compute(String.valueOf(userId), (id, count) -> count == null ? 1 : ++count);

          /*

          Without lambdas implementation of incrementing

          int counter;
          if (countMap.containsKey(userId))
            counter = countMap.get(userId) + 1;
          else
            counter = 1;
          countMap.put(userId, counter);

          */

          // Send formatted will enable markdown
          String message = String.format("%s, your count is now *%d*!", ctx.user().getUserName(), counter);
          silent.send(message, ctx.chatId());
        })
        .build();
  }
```

After successfully adding that ability to your bot, try to /count and watch as the number increases at every message.
Other important functions in the `db` object:
* getSet - gets a set of data
* getList - gets a list of data
* summary - gets a summary of the present structs

Be sure to check out [DBContext](../../telegrambots-abilities/src/main/java/org/telegram/abilitybots/api/db/DBContext.java) for all the implemented methods.