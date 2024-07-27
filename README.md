# FFmpeg-Wrapper

FFmpeg-Wrapper is designed to facilitate the use of FFmpeg and FFprobe functionalities within Java applications. It provides an object-oriented interface for building and executing basic FFmpeg and FFprobe commands for processing video/audio files and extracting their metadata. It also provides utility methods for some commonly used multimedia operations, such as reducing video resolution, reducing file size or changing media codecs.



## Usage

```java
// this will put the cut the the second minute of the input.mp4 to output.mp4.
   FFmpegUtil.cutPlayableMedia("input.mp4", "output.mp4", "00:01:00", "00:01:00");

// this will reduce the resolution of the input.mp4 and stores the new one into output.mp4.
   FFmpegUtil.changeResolution("input.mp4", "output.mp4", VideoResolution.P720);

// this will reduce the file size of input.mp4 to approximately 15MB.
   FFmpegUtil.reduceFileSize("input.mp4", "output.mp4", 15 * 1_048_576);

//this will extract the resolution of the input.mp4 and return it as a VideoResolution object.
   FFprobeUtil.extractResolution("input.mp4");
```
