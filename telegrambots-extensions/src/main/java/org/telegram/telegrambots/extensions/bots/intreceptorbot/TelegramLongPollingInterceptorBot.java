package org.telegram.telegrambots.extensions.bots.intreceptorbot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.IInterceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.IInterceptorsRegistry;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.InterceptorRegistry;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.PayloadStorage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.SetStickerSetThumb;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * <pre>
 *     <code>
 *         public class ExampleBOt extends TelegramLongPollingInterceptorBot{
 *              public ExampleBOt(){
 *                  registerInterceptor(new DurationLoggerInterceptor());
 *              }
 *
 *             @Override
 *             public void onUpdateReceived(Update update){
 *                  User user = (User)getPayloadStorage.getPayload(User.class).getData();
 *                  // your code
 *             }
 *
 *             @Override
 *             public String getBotUsername(){
 *                 return "username";
 *             }
 *
 *             @Override
 *             public String getBotToken(){
 *                 return "token";
 *             }
 *         }
 *     </code>
 * </pre>
 * This class add ability to add interceptors
 * @author Fedechkin Alexey aka centralhardware
 */
public abstract class TelegramLongPollingInterceptorBot extends TelegramLongPollingBot implements IInterceptorsRegistry {

    private final IInterceptorsRegistry interceptorsRegistry = new InterceptorRegistry();
    private final List<Object> sentMessages = new ArrayList<>();
    private final boolean allowInterceptOutgoingObjects;

    public TelegramLongPollingInterceptorBot(){
        this(false);
    }

    public TelegramLongPollingInterceptorBot(boolean allowInterceptOutgoingObjects){
        this(new DefaultBotOptions(), allowInterceptOutgoingObjects);
    }

    public TelegramLongPollingInterceptorBot(DefaultBotOptions options, boolean allowInterceptOutgoingObjects){
        super(options);
        this.allowInterceptOutgoingObjects = allowInterceptOutgoingObjects;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (interceptorsRegistry.callBeforeInterceptor(update, interceptorsRegistry.getPayloadStorage())) return;

        onUpdateReceived(update);

        if (allowInterceptOutgoingObjects) {
            interceptorsRegistry.callAfterInterceptor(update, interceptorsRegistry.getPayloadStorage(), sentMessages);
        } else {
            interceptorsRegistry.callAfterInterceptor(update, interceptorsRegistry.getPayloadStorage(), null);
        }
        sentMessages.clear();
    }

    @Override
    public void registerInterceptor(IInterceptor iInterceptor) {
        interceptorsRegistry.registerInterceptor(iInterceptor);
    }

    @Override
    public PayloadStorage getPayloadStorage() {
        return interceptorsRegistry.getPayloadStorage();
    }

    @Override
    public void clearStorage() {
        this.interceptorsRegistry.clearStorage();
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws TelegramApiException {
        interceptOutgoingObjects(() -> method);

        super.executeAsync(method, callback);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws TelegramApiException {
        interceptOutgoingObjects(() -> method);

        return super.executeAsync(method);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
        interceptOutgoingObjects(() -> method);

        return super.execute(method);
    }

    @Override
    public Boolean execute(SetChatPhoto setChatPhoto) throws TelegramApiException {
        interceptOutgoingObjects(() -> setChatPhoto);

        return super.execute(setChatPhoto);
    }

    @Override
    public List<Message> execute(SendMediaGroup sendMediaGroup) throws TelegramApiException {
        interceptOutgoingObjects(() -> sendMediaGroup);

        return super.execute(sendMediaGroup);
    }

    @Override
    public Boolean execute(AddStickerToSet addStickerToSet) throws TelegramApiException {
        interceptOutgoingObjects(() -> addStickerToSet);

        return super.execute(addStickerToSet);
    }

    @Override
    public Boolean execute(SetStickerSetThumb setStickerSetThumb) throws TelegramApiException {
        interceptOutgoingObjects(() -> setStickerSetThumb);

        return super.execute(setStickerSetThumb);
    }

    @Override
    public Boolean execute(CreateNewStickerSet createNewStickerSet) throws TelegramApiException {
        interceptOutgoingObjects(() -> createNewStickerSet);

        return super.execute(createNewStickerSet);
    }

    @Override
    public File execute(UploadStickerFile uploadStickerFile) throws TelegramApiException {
        interceptOutgoingObjects(() -> uploadStickerFile);

        return super.execute(uploadStickerFile);
    }

    @Override
    public Serializable execute(EditMessageMedia editMessageMedia) throws TelegramApiException {
        interceptOutgoingObjects(() -> editMessageMedia);

        return super.execute(editMessageMedia);
    }

    @Override
    public Message execute(SendAnimation sendAnimation) throws TelegramApiException {
        interceptOutgoingObjects(() -> sendAnimation);

        return super.execute(sendAnimation);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendDocument sendDocument) {
        interceptOutgoingObjects(() -> sendDocument);

        return super.executeAsync(sendDocument);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendPhoto sendPhoto) {
        interceptOutgoingObjects(() -> sendPhoto);

        return super.executeAsync(sendPhoto);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideo sendVideo) {
        interceptOutgoingObjects(() -> sendVideo);

        return super.executeAsync(sendVideo);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote) {
        interceptOutgoingObjects(() -> sendVideoNote);

        return super.executeAsync(sendVideoNote);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendSticker sendSticker) {
        interceptOutgoingObjects(() -> sendSticker);

        return super.executeAsync(sendSticker);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAudio sendAudio) {
        interceptOutgoingObjects(() -> sendAudio);

        return super.executeAsync(sendAudio);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVoice sendVoice) {
        interceptOutgoingObjects(() -> sendVoice);

        return super.executeAsync(sendVoice);
    }

    @Override
    public CompletableFuture<List<Message>> executeAsync(SendMediaGroup sendMediaGroup) {
        interceptOutgoingObjects(() -> sendMediaGroup);

        return super.executeAsync(sendMediaGroup);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetChatPhoto setChatPhoto) {
        interceptOutgoingObjects(() -> setChatPhoto);

        return super.executeAsync(setChatPhoto);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(AddStickerToSet addStickerToSet) {
        interceptOutgoingObjects(() -> addStickerToSet);

        return super.executeAsync(addStickerToSet);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetStickerSetThumb setStickerSetThumb) {
        interceptOutgoingObjects(() -> setStickerSetThumb);

        return super.executeAsync(setStickerSetThumb);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(CreateNewStickerSet createNewStickerSet) {
        interceptOutgoingObjects(() -> createNewStickerSet);

        return super.executeAsync(createNewStickerSet);
    }

    @Override
    public CompletableFuture<File> executeAsync(UploadStickerFile uploadStickerFile) {
        interceptOutgoingObjects(() -> uploadStickerFile);

        return super.executeAsync(uploadStickerFile);
    }

    @Override
    public CompletableFuture<Serializable> executeAsync(EditMessageMedia editMessageMedia) {
        interceptOutgoingObjects(() -> editMessageMedia);

        return super.executeAsync(editMessageMedia);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAnimation sendAnimation) {
        interceptOutgoingObjects(() -> sendAnimation);

        return super.executeAsync(sendAnimation);
    }

    private void interceptOutgoingObjects(Supplier<Object> supplier){
        if (!allowInterceptOutgoingObjects) return;

        sentMessages.add(supplier.get());
    }
}
