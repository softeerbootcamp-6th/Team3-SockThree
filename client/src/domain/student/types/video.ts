export interface VideoData {
  id: number;
  videoTitle: string;
  duration: number;
  updatedDate: Date;
  watchedTime?: number;
  isCompleted?: boolean;
}

export interface VideoListData {
  contentsId?: number;
  contentsTitle?: string;
  videos?: VideoData[];
  completedVideos?: number;
  totalVideos?: number;
}
