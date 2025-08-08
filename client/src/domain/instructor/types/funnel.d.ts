export type FunnelContext = {
  category: string;
  subCategory: string;
  level: string;
  duration: {
    startDate: Date;
    endDate: Date;
  };
  maxHeadCount: number;
  uploadTimes: string[];
  price: number;
  introduction: {
    name: string;
    description: string;
    simpleDescription: string;
  };
  curriculum: string;
  imageUrl: string;
};
