package org.telegram.telegrambots.meta.api.objects.polls;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.LivePhoto;
import org.telegram.telegrambots.meta.api.objects.Venue;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.location.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 10.0
 */
public class TestPollMedia {

    @Test
    public void testPollMediaDefaultConstructor() {
        PollMedia pollMedia = new PollMedia();

        assertNull(pollMedia.getAnimation());
        assertNull(pollMedia.getAudio());
        assertNull(pollMedia.getDocument());
        assertNull(pollMedia.getLivePhoto());
        assertNull(pollMedia.getLocation());
        assertNull(pollMedia.getPhoto());
        assertNull(pollMedia.getSticker());
        assertNull(pollMedia.getVenue());
        assertNull(pollMedia.getVideo());
    }

    @Test
    public void testPollMediaWithLivePhoto() {
        LivePhoto livePhoto = new LivePhoto();
        PollMedia pollMedia = PollMedia.builder()
                .livePhoto(livePhoto)
                .build();

        assertEquals(livePhoto, pollMedia.getLivePhoto());
        assertNull(pollMedia.getVideo());
    }

    @Test
    public void testPollMediaWithLocation() {
        Location location = Location.builder()
                .latitude(51.5074)
                .longitude(-0.1278)
                .build();
        PollMedia pollMedia = PollMedia.builder()
                .location(location)
                .build();

        assertEquals(location, pollMedia.getLocation());
        assertNull(pollMedia.getLivePhoto());
    }

    @Test
    public void testPollMediaWithVideo() {
        Video video = new Video();
        PollMedia pollMedia = PollMedia.builder()
                .video(video)
                .build();

        assertEquals(video, pollMedia.getVideo());
        assertNull(pollMedia.getAnimation());
    }

    @Test
    public void testPollMediaWithVenue() {
        Venue venue = new Venue();
        PollMedia pollMedia = PollMedia.builder()
                .venue(venue)
                .build();

        assertEquals(venue, pollMedia.getVenue());
    }

    @Test
    public void testPollMediaSetter() {
        PollMedia pollMedia = new PollMedia();
        Video video = new Video();
        pollMedia.setVideo(video);

        assertEquals(video, pollMedia.getVideo());
    }

    @Test
    public void testPollMediaWithLink() {
        org.telegram.telegrambots.meta.api.objects.Link link =
                org.telegram.telegrambots.meta.api.objects.Link.builder()
                        .url("https://example.com")
                        .build();
        PollMedia pollMedia = PollMedia.builder()
                .link(link)
                .build();

        assertEquals(link, pollMedia.getLink());
    }

    @Test
    public void testPollMediaLinkNullByDefault() {
        PollMedia pollMedia = new PollMedia();

        assertNull(pollMedia.getLink());
    }
}
