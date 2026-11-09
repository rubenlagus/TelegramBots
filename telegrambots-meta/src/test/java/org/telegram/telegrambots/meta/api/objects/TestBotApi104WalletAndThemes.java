package org.telegram.telegrambots.meta.api.objects;

import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.GetChatShareThemeBackdrops;
import org.telegram.telegrambots.meta.api.objects.chat.ChatFullInfo;
import org.telegram.telegrambots.meta.api.objects.chat.sharetheme.ChatShareTheme;
import org.telegram.telegrambots.meta.api.objects.chat.sharetheme.ChatShareThemeEmoji;
import org.telegram.telegrambots.meta.api.objects.chat.sharetheme.ChatShareThemeUniqueGift;
import org.telegram.telegrambots.meta.api.objects.emoji.EmojiTypeCustomEmoji;
import org.telegram.telegrambots.meta.api.objects.emoji.EmojiTypeEmoji;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.stories.StoryArea;
import org.telegram.telegrambots.meta.api.objects.stories.StoryAreaPosition;
import org.telegram.telegrambots.meta.api.objects.stories.area.StoryAreaTypeLink;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import static org.junit.jupiter.api.Assertions.*;

public class TestBotApi104WalletAndThemes {
    private final JsonMapper mapper = new JsonMapper();
    private static final String POSITION = "{\"x_percentage\":1.0,\"y_percentage\":2.0,\"width_percentage\":3.0,"
            + "\"height_percentage\":4.0,\"rotation_angle\":5.0,\"corner_radius_percentage\":6.0}";
    private static final String GIFT = "{\"base_name\":\"Gift\",\"name\":\"Gift-1\",\"number\":1,\"gift_id\":\"g\"}";

    @Test
    public void walletCardAndTransferRoundTrip() throws Exception {
        String wallet = "{\"address\":\"wallet\",\"amount\":\"1234567890123456789012345\",\"stickers\":[{\"position\":"
                + POSITION + ",\"nft\":{\"address\":\"nft\",\"index\":\"999999999999999999999\",\"name\":\"N\","
                + "\"description\":\"D\",\"attributes\":[{\"trait_type\":\"color\",\"value\":\"blue\"}],\"gift\":" + GIFT + "}}]}";
        ChatFullInfo chat = mapper.readValue("{\"id\":1,\"type\":\"private\",\"ton_wallet_card\":" + wallet + "}", ChatFullInfo.class);
        assertEquals("1234567890123456789012345", chat.getTonWalletCard().getAmount());
        assertEquals("999999999999999999999", chat.getTonWalletCard().getStickers().get(0).getNft().getIndex());
        assertEquals("blue", chat.getTonWalletCard().getStickers().get(0).getNft().getAttributes().get(0).getValue());
        assertEquals(mapper.readTree(wallet), mapper.readTree(mapper.writeValueAsString(chat.getTonWalletCard())));

        Message message = mapper.readValue("{\"message_id\":1,\"date\":1,\"chat\":{\"id\":1,\"type\":\"private\"},"
                + "\"paid_messages_waived\":{},\"ton_wallet_transfer\":{\"transaction_id\":\"\",\"peer_address\":\"p\",\"amount\":\"-5\"}}", Message.class);
        assertEquals("", message.getTonWalletTransfer().getTransactionId());
        assertEquals("-5", message.getTonWalletTransfer().getAmount());
        assertNotNull(message.getPaidMessagesWaived());
        assertEquals(message.getTonWalletTransfer(), mapper.readValue(mapper.writeValueAsString(message), Message.class).getTonWalletTransfer());
        assertNotNull(mapper.readValue(mapper.writeValueAsString(message), Message.class).getPaidMessagesWaived());
    }

    @Test
    public void shareThemeAndEmojiVariantsRoundTrip() throws Exception {
        String json = "{\"type\":\"emoji\",\"emojis\":[{\"type\":\"emoji\",\"emoji\":\"😀\"},"
                + "{\"type\":\"custom_emoji\",\"custom_emoji_id\":\"123\"}],\"background_backdrop_id\":5}";
        ChatShareThemeEmoji theme = (ChatShareThemeEmoji) mapper.readValue(json, ChatShareTheme.class);
        assertInstanceOf(EmojiTypeEmoji.class, theme.getEmojis().get(0));
        assertInstanceOf(EmojiTypeCustomEmoji.class, theme.getEmojis().get(1));
        assertEquals(mapper.readTree(json), mapper.readTree(mapper.writeValueAsString(theme)));
        ChatFullInfo chat = mapper.readValue("{\"id\":1,\"type\":\"private\",\"share_theme\":" + json + "}", ChatFullInfo.class);
        assertInstanceOf(ChatShareThemeEmoji.class, chat.getShareTheme());
        String unique = "{\"type\":\"unique_gift\",\"gift\":" + GIFT + "}";
        assertInstanceOf(ChatShareThemeUniqueGift.class, mapper.readValue(unique, ChatShareTheme.class));
        assertNull(mapper.readValue("{\"type\":\"future\"}", ChatShareTheme.class));
    }

    @Test
    public void backdropMethodHandlesResponseAndErrors() throws Exception {
        GetChatShareThemeBackdrops method = new GetChatShareThemeBackdrops();
        assertEquals("getChatShareThemeBackdrops", method.getMethod());
        assertEquals(7, method.deserializeResponse("{\"ok\":true,\"result\":{\"backdrops\":[{\"id\":7,\"name\":\"Blue\","
                + "\"colors\":{\"center_color\":1,\"edge_color\":2,\"symbol_color\":3,\"text_color\":4}}]}}").getBackdrops().get(0).getId());
        assertThrows(TelegramApiRequestException.class, () -> method.deserializeResponse("{\"ok\":false,\"error_code\":400,\"description\":\"bad\"}"));
    }

    @Test
    public void storyPositionOldAndNewApisRemainUsable() throws Exception {
        StoryAreaPosition old = new StoryAreaPosition(1f, 2f, 3f, 4f, 5f, 6f);
        StoryArea oldArea = new StoryArea(old, new StoryAreaTypeLink("https://example.com"));
        assertSame(old, oldArea.getPosition());
        assertEquals(1f, oldArea.getAreaPosition().getXPercentage());
        StoryAreaPosition oldBuilder = StoryAreaPosition.builder().xPercentage(1f).yPercentage(2f).widthPercentage(3f)
                .heightPercentage(4f).rotationAngle(5f).cornerRadiusPercentage(6f).build();
        assertEquals(old, oldBuilder);
        AreaPosition position = AreaPosition.builder().xPercentage(1f).yPercentage(2f).widthPercentage(3f)
                .heightPercentage(4f).rotationAngle(5f).cornerRadiusPercentage(6f).build();
        StoryArea area = StoryArea.builder().position(position).type(new StoryAreaTypeLink("https://example.com")).build();
        assertSame(position, area.getPosition());
        assertSame(position, area.getAreaPosition());
        area.setAreaPosition(position);
        assertSame(position, area.getPosition());
        assertSame(position, StoryArea.builder().areaPosition(position).type(new StoryAreaTypeLink("https://example.com")).build().getPosition());
        assertEquals(mapper.readTree(POSITION), mapper.readTree(mapper.writeValueAsString(position)));
        assertEquals(position, mapper.readValue(POSITION, AreaPosition.class));
        assertEquals(old, mapper.readValue(mapper.writeValueAsString(oldArea), StoryArea.class).getPosition());
    }
}