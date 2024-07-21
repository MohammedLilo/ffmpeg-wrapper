package com.ffmpeg_wrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffmpeg_wrapper.mapping.FFprobeOutput;
import com.ffmpeg_wrapper.mapping.Format;
import com.ffmpeg_wrapper.mapping.Stream;
import com.ffmpeg_wrapper.mapping.Tags;

public class FFprobeUtil {
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Extracts all metadata from a given media file.
	 *
	 * @param filePath The path to the media file.
	 * @return A list containing all metadata.
	 */
	public static FFprobeOutput extractMetadata(String filePath) {
		FFprobe ffprobe = FFprobe.builder().inputFilePath(filePath).entryToShow(StreamMetadata.ALL)
				.ffprobeOutputFormat(FFprobeOutputFormat.JSON).isShowFormat(true).build();
		ffprobe.probe();
		FFprobeOutput output = null;
		try {
			output = mapper.readValue(ffprobe.getProbeResult(), FFprobeOutput.class);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}
		return output;
	}

	public static List<Stream> extractStreams(String filePath) {
		FFprobe ffprobe = FFprobe.builder().inputFilePath(filePath).entryToShow(StreamMetadata.ALL)
				.ffprobeOutputFormat(FFprobeOutputFormat.JSON).build();
		ffprobe.probe();
		FFprobeOutput output = null;
		try {
			output = mapper.readValue(ffprobe.getProbeResult(), FFprobeOutput.class);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}
		return output.getStreams();
	}

	public static List<Stream> extractStreamsByType(String filePath, StreamType streamType) {
		List<Stream> allStreams = extractStreams(filePath);
		List<Stream> specificStreams = new ArrayList<>();

		for (int i = 0; i < allStreams.size(); i++) {
			if (allStreams.get(i).getCodecType().charAt(0) == streamType.toString().charAt(0)) {
				specificStreams.add(allStreams.get(i));
			}
		}
		return specificStreams;
	}

	/**
	 * Extracts a specific metadata field of a stream from a given media file.
	 *
	 * @param filePath      The path to the media file.
	 * @param metadataField The list of metadata fields to extract.
	 * @param fromStream    the stream from which to extract the specific metadata..
	 * @param index         the index of the stream from which to extract the
	 *                      specific metadata (use in case multiple streams are
	 *                      present in the media file).
	 * 
	 * @return the stream from which to extract the specific metadata.
	 */
	public static Object extractSpecificMetadata(String filePath, StreamMetadata metadataField, StreamType fromStream,
			int index) {
		FFprobe ffprobe = FFprobe.builder().inputFilePath(filePath).selectedStream(fromStream)
				.entryToShow(metadataField).ffprobeOutputFormat(FFprobeOutputFormat.JSON).build();
		ffprobe.probe();
		FFprobeOutput output = null;
		try {
			output = mapper.readValue(ffprobe.getProbeResult(), FFprobeOutput.class);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}

		if (output != null && !output.getStreams().isEmpty()) {
			Stream stream = output.getStreams().get(index);
			try {
				Field field = Stream.class.getDeclaredField(TextUtils.snakeToCamelCase(metadataField.getMetadata()));
				field.setAccessible(true);
				return field.get(stream);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Extracts format information from a given media file.
	 *
	 * @param filePath The path to the media file.
	 * @return A Format object containing format information.
	 */
	public static Format extractFormat(String filePath) {
		FFprobe ffprobe = FFprobe.builder().inputFilePath(filePath).ffprobeOutputFormat(FFprobeOutputFormat.JSON)
				.isShowFormat(true).build();
		ffprobe.probe();
		FFprobeOutput output = null;
		try {
			output = mapper.readValue(ffprobe.getProbeResult(), FFprobeOutput.class);

		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}
		return output.getFormat();
	}

	/**
	 * Extracts tags from a given media file.
	 *
	 * @param filePath The path to the media file.
	 * @return A Tags object containing format information.
	 */
	public static Tags extractTags(String filePath) {
		FFprobe ffprobe = FFprobe.builder().inputFilePath(filePath).ffprobeOutputFormat(FFprobeOutputFormat.JSON)
				.isShowFormat(true).build();
		ffprobe.probe();
		FFprobeOutput output = null;
		try {
			output = mapper.readValue(ffprobe.getProbeResult(), FFprobeOutput.class);

		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}
		return output.getFormat().getTags();
	}

	/**
	 * Extracts bitrates of all streams in a given media file.
	 *
	 * @param filePath The path to the media file.
	 * @return A map where the key is the stream index and the value is the bitrate.
	 */
	public static long extractTotalBitrate(String filePath) {
		return extractFormat(filePath).getTotalBitRate();
	}

	/**
	 * Extracts the duration of the media file.
	 *
	 * @param filePath The path to the media file.
	 * @return The duration of the media file as a string.
	 */
	public static double extractDuration(String filePath) {
		return extractFormat(filePath).getDuration();
	}

	/**
	 * Extracts the video resolution from a given media file.
	 *
	 * @param filePath The path to the media file.
	 * @return The video resolution as a string.
	 */
	public static VideoResolution extractResolution(String filePath) {
		int width = (Integer) extractSpecificMetadata(filePath, StreamMetadata.WIDTH, StreamType.VIDEO, 0);
		int height = (Integer) extractSpecificMetadata(filePath, StreamMetadata.HEIGHT, StreamType.VIDEO, 0);

		return new VideoResolution(width, height);
	}

	/**
	 * Checks if a media file contains a video stream.
	 *
	 * @param filePath The path to the media file.
	 * @return True if the file contains a video stream, false otherwise.
	 */
	public static boolean hasVideoStream(String filePath) {
		return extractSpecificMetadata(filePath, StreamMetadata.CODEC_TYPE, StreamType.VIDEO, 0) != null;

	}

	/**
	 * Checks if a media file contains an audio stream.
	 *
	 * @param filePath The path to the media file.
	 * @return True if the file contains an audio stream, false otherwise.
	 */
	public static boolean hasAudioStream(String filePath) {
		return extractSpecificMetadata(filePath, StreamMetadata.CODEC_TYPE, StreamType.AUDIO, 0) != null;
	}

	/**
	 * Checks if a media file contains an attachment stream.
	 *
	 * @param filePath The path to the media file.
	 * @return True if the file contains an attachment stream, false otherwise.
	 */
	public static boolean hasAttachmentStream(String filePath) {
		return extractSpecificMetadata(filePath, StreamMetadata.CODEC_TYPE, StreamType.ATTACHMENT, 0) != null;
	}

	/**
	 * Checks if a media file contains an subtitle stream.
	 *
	 * @param filePath The path to the media file.
	 * @return True if the file contains an subtitle stream, false otherwise.
	 */
	public static boolean hasSubtitleStream(String filePath) {
		return extractSpecificMetadata(filePath, StreamMetadata.CODEC_TYPE, StreamType.SUBTITLE, 0) != null;
	}

}
