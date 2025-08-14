// 텍스트를 최대 길이로 잘라내는 함수(한국어 입력 시 고려)
export const truncateToMaxLength = (text: string, maxLength: number) => {
  if (text.length > maxLength) {
    text = text.slice(0, maxLength);
  }
  return text;
};
