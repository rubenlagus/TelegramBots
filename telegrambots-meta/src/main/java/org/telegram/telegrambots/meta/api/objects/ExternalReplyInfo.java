package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.games.Game;
import org.telegram.telegrambots.meta.api.objects.giveaway.Giveaway;
import org.telegram.telegrambots.meta.api.objects.giveaway.GiveawayWinners;
import org.telegram.telegrambots.meta.api.objects.messageorigin.MessageOrigin;
import org.telegram.telegrambots.meta.api.objects.payments.Invoice;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.api.objects.stories.Story;

import java.util.List;

/**
 * This object contains information about a message that is being replied to, which may come from another chat or forum topic.
 * @author Ruben Bermudez
 * @version 7.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExternalReplyInfo implements BotApiObject {
    private static final String ORIGIN_FIELD = "origin";
    private static final String CHAT_FIELD = "chat";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String LINK_PREVIEW_OPTIONS_FIELD = "link_preview_options";
    private static final String ANIMATION_FIELD = "animation";
    private static final String AUDIO_FIELD = "audio";
    private static final String DOCUMENT_FIELD = "document";
    private static final String PHOTO_FIELD = "photo";
    private static final String STICKER_FIELD = "sticker";
    private static final String STORY_FIELD = "story";
    private static final String VIDEO_FIELD = "video";
    private static final String VIDEO_NOTE_FIELD = "video_note";
    private static final String VOICE_FIELD = "voice";
    private static final String HAS_MEDIA_SPOILER_FIELD = "has_media_spoiler";
    private static final String CONTACT_FIELD = "contact";
    private static final String DICE_FIELD = "dice";
    private static final String GAME_FIELD = "game";
    private static final String GIVEAWAY_FIELD = "giveaway";
    private static final String GIVEAWAY_WINNERS_FIELD = "giveaway_winners";
    private static final String INVOICE_FIELD = "invoice";
    private static final String LOCATION_FIELD = "location";
    private static final String POLL_FIELD = "poll";
    private static final String VENUE_FIELD = "venue";

    /**
     * Origin of the message replied to by the given message
     */
    @JsonProperty(ORIGIN_FIELD)
    private MessageOrigin origin;
    /**
     * Optional.
     * Chat the original message belongs to. Available only if the chat is a supergroup or a channel.
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Optional.
     * Unique message identifier inside the original chat. Available only if the original chat is a supergroup or a channel.
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Optional.
     * Options used for link preview generation for the original message, if it is a text message
     */
    @JsonProperty(LINK_PREVIEW_OPTIONS_FIELD)
    private LinkPreviewOptions linkPreviewOptions;
    /**
     * Optional.
     * Message is an animation, information about the animation
     */
    @JsonProperty(ANIMATION_FIELD)
    private Animation animation;
    /**
     * Optional.
     * Message is an audio file, information about the file
     */
    @JsonProperty(AUDIO_FIELD)
    private Audio audio;  
    /**
     * Optional.
     * Message is a general file, information about the file
     */
    @JsonProperty(DOCUMENT_FIELD)
    private Document document;
    /**
     * Optional.
     * Message is a photo, available sizes of the photo
     */
    @JsonProperty(PHOTO_FIELD)
    private List<PhotoSize> photo;
    /**
     * Optional.
     * Message is a sticker, information about the sticker
     */
    @JsonProperty(STICKER_FIELD)
    private Sticker sticker;
    /**
     * Optional.
     * Message is a forwarded story
     */
    @JsonProperty(STORY_FIELD)
    private Story story;
    /**
     * Optional.
     * Message is a video, information about the video
     */
    @JsonProperty(VIDEO_FIELD)
    private Video video;
    /**
     * Optional.
     * Message is a video note, information about the video message
     */
    @JsonProperty(VIDEO_NOTE_FIELD)
    private VideoNote videoNote;
    /**
     * Optional.
     * Message is a voice message, information about the file
     */
    @JsonProperty(VOICE_FIELD)
    private Voice voice;
    /**
     * Optional.
     * True, if the message media is covered by a spoiler animation
     */
    @JsonProperty(HAS_MEDIA_SPOILER_FIELD)
    private Boolean hasMediaSpoiler;
    /**
     * Optional.
     * Message is a shared contact, information about the contact
     */
    @JsonProperty(CONTACT_FIELD)
    private Contact contact;
    /**
     * Optional.
     * Message is a dice with random value
     */
    @JsonProperty(DICE_FIELD)
    private Dice dice;
    /**
     * Optional.
     * Message is a game, information about the game.
     */
    @JsonProperty(GAME_FIELD)
    private Game game;
    /**
     * Optional.
     * Message is a scheduled giveaway, information about the giveaway
     */
    @JsonProperty(GIVEAWAY_FIELD)
    private Giveaway giveaway;
    /**
     * Optional.
     * A giveaway with public winners was completed
     */
    @JsonProperty(GIVEAWAY_WINNERS_FIELD)
    private GiveawayWinners giveawayWinners;
    /**
     * Optional.
     * Message is an invoice for a payment, information about the invoice.
     */
    @JsonProperty(INVOICE_FIELD)
    private Invoice invoice;
    /**
     * Optional.
     * Message is a shared location, information about the location
     */
    @JsonProperty(LOCATION_FIELD)
    private Location location;
    /**
     * Optional.
     * Message is a native poll, information about the poll
     */
    @JsonProperty(POLL_FIELD)
    private Poll poll;
    /**
     * Optional.
     * Message is a venue, information about the venue
     */
    @JsonProperty(VENUE_FIELD)
    private Venue venue;  


    @JsonIgnore
    public boolean hasSticker() {
        return sticker != null;
    }

    @JsonIgnore
    public boolean isGroupMessage() {
        return chat.isGroupChat();
    }

    @JsonIgnore
    public boolean isUserMessage() {
        return chat.isUserChat();
    }

    @JsonIgnore
    public boolean isChannelMessage() {
        return chat.isChannelChat();
    }

    @JsonIgnore
    public boolean isSuperGroupMessage() {
        return chat.isSuperGroupChat();
    }

    @JsonIgnore
    public Long getChatId() {
        return chat.getId();
    }

    @JsonIgnore
    public boolean hasDocument() {
        return this.document != null;
    }

    @JsonIgnore
    public boolean hasVideo() {
        return this.video != null;
    }

    @JsonIgnore
    public boolean hasAudio(){
        return this.audio != null;
    }

    @JsonIgnore
    public boolean hasVoice(){
        return this.voice != null;
    }

    @JsonIgnore
    public boolean hasLocation() {
        return location != null;
    }

    @JsonIgnore
    private boolean hasGame() {
        return game != null;
    }

    @JsonIgnore
    public boolean hasPhoto() {
        return photo != null && !photo.isEmpty();
    }

    @JsonIgnore
    public boolean hasInvoice() {
        return invoice != null;
    }

    @JsonIgnore
    public boolean hasContact() {
        return contact != null;
    }

    @JsonIgnore
    public boolean hasVideoNote() {
        return videoNote != null;
    }

    @JsonIgnore
    public boolean hasAnimation() {
        return animation != null;
    }

    @JsonIgnore
    public boolean hasPoll() {
        return poll != null;
    }

    @JsonIgnore
    public boolean hasDice() {
        return dice != null;
    }

    @JsonIgnore
    private boolean hasStory() {
        return story != null;
    }
}
