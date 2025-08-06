import * as z from "zod";

export const funnelSchema = z.object({
  category: z.string().min(1, "카테고리를 선택해주세요"),
  subCategory: z.string().min(1, "세부 카테고리를 선택해주세요"),
  level: z.string().min(1, "수준을 선택해주세요"),
  startDate: z.string().min(1, "시작일을 선택해주세요"),
  endDate: z.string().min(1, "종료일을 선택해주세요"),
  maxHeadCount: z.number().min(1, "최소 1명 이상이어야 합니다"),
  uploadTimes: z.array(z.string()).min(1, "업로드 일정을 선택해주세요"),
  price: z.number().min(0, "가격은 0 이상이어야 합니다"),
  introduction: z.object({
    name: z.string().min(1, "강좌 이름을 입력해주세요"),
    description: z.string().optional(),
    simpleDescription: z.string().optional(),
  }),
  curriculum: z.string().min(1, "커리큘럼을 입력해주세요"),
  imageUrl: z.string().url("유효한 이미지 URL이 필요합니다"),
});

export type FunnelFormSchema = z.infer<typeof funnelSchema>;
