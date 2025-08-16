import Hls from "hls.js";
import { useEffect, useRef } from "react";

interface VideoPlayerProps {
  videoUrl: string;
}
const VideoPlayer = ({ videoUrl }: VideoPlayerProps) => {
  const videoRef = useRef<HTMLVideoElement | null>(null);

  useEffect(() => {
    if (videoRef.current && Hls.isSupported()) {
      const hls = new Hls();
      hls.loadSource(videoUrl);
      hls.attachMedia(videoRef.current);
    }
  }, [videoUrl]);

  return (
    <div>
      <video ref={videoRef} controls className="h-auto w-[62.5rem]" />
    </div>
  );
};

export default VideoPlayer;
