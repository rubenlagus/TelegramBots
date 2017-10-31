# Bot Recovery
With recovery, we specifically mean recovering the DB in-case of false data being committed. This is a neat feature supported by DBContext, you can /backup and /recover your bot whenever needed.

Once you /backup, the bot will respond back with a valid JSON object that represents all the data in the DB.

On /recover, the bot will ask for the JSON file. A reply to the message with the file attached will recover the bot with the previous state DB.

Try to experiment using the counter ability introduced in [[Database Handling|Database-Handling]]!