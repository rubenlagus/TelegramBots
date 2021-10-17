### Using HTTP proxy

HTTP proxy support implemented since version 3.6.1

First of all you need to override constructor with `DefaultBotOptions` argument while inheriting from `AbilityBot` or `TelegramLongPollingBot`

```java
public class MyBot extends AbilityBot {

    protected MyBot(String botToken, String botUsername, DefaultBotOptions botOptions) {
        super(botToken, botUsername, botOptions);
    }

    public int creatorId() {
        return 0;
    }

    public Ability pingPong() {
        return Ability
                .builder()
                .name("ping")
                .info("ping pong")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("pong", ctx.chatId()))
                .build();
    }

}
```

Now you are able to set up your proxy

#### Without authentication

```java
public class Main {

    private static String BOT_NAME = "My test bot";
    private static String BOT_TOKEN = "..." /* your bot's token here */;

    private static String PROXY_HOST = "..." /* proxy host */;
    private static Integer PROXY_PORT = 3128 /* proxy port */;

    public static void main(String[] args) {
        try {
            // Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSessioin.class);

            // Set up Http proxy
            DefaultBotOptions botOptions = new DefaultBotOptions();            

            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(PROXY_PORT);
            // Select proxy type: [HTTP|SOCKS4|SOCKS5] (default: NO_PROXY)
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

            // Register your newly created AbilityBot
            MyBot bot = new MyBot(BOT_TOKEN, BOT_NAME, botOptions);

            botsApi.registerBot(bot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

```


#### With authentication

```java
public class Main {

    private static String BOT_NAME = "My test bot";
    private static String BOT_TOKEN = "..." /* your bot's token here */;

    private static String PROXY_HOST = "..." /* proxy host */;
    private static Integer PROXY_PORT = 3128 /* proxy port */;
    private static String PROXY_USER = "..." /* proxy user */;
    private static String PROXY_PASSWORD = "..." /* proxy password */;

    public static void main(String[] args) {
        try {

            // Create the Authenticator that will return auth's parameters for proxy authentication
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray());
                }
            });
        
            // Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Set up Http proxy
            DefaultBotOptions botOptions = new DefaultBotOptions();          

            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(PROXY_PORT);
            // Select proxy type: [HTTP|SOCKS4|SOCKS5] (default: NO_PROXY)
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

            // Register your newly created AbilityBot
            MyBot bot = new MyBot(BOT_TOKEN, BOT_NAME, botOptions);

            botsApi.registerBot(bot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
```

If you need something more complex than one proxy, then you can create more complex Authenticator that will check host and other parameters of proxy and return auth values based on them (for more information see code of java.net.Authenticator class)
