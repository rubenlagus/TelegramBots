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

#### without authentication

```java
public class Main {

    private static String BOT_NAME = "My test bot";
    private static String BOT_TOKEN = "..." /* your bot's token here */;

    private static String PROXY_HOST = "..." /* proxy host */;
    private static Integer PROXY_PORT = 3128 /* proxy port */;

    public static void main(String[] args) {
        try {

            ApiContextInitializer.init();

            // Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi();

            // Set up Http proxy
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            HttpHost httpHost = new HttpHost(PROXY_HOST, PROXY_PORT);

            RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(false).build();
            botOptions.setRequestConfig(requestConfig);
            botOptions.setHttpProxy(httpHost);

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

            ApiContextInitializer.init();

            // Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi();

            // Set up Http proxy
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(PROXY_HOST, PROXY_PORT),
                    new UsernamePasswordCredentials(PROXY_USER, PROXY_PASSWORD));

            HttpHost httpHost = new HttpHost(PROXY_HOST, PROXY_PORT);

            RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(true).build();
            botOptions.setRequestConfig(requestConfig);
            botOptions.setCredentialsProvider(credsProvider);
            botOptions.setHttpProxy(httpHost);

            // Register your newly created AbilityBot
            MyBot bot = new MyBot(BOT_TOKEN, BOT_NAME, botOptions);

            botsApi.registerBot(bot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
```