// Date 객체 format과 관련한 함수
export const formatDate = (
  dateInput: Date | string | number,
  delimiter: "-" | ".",
  fullYear: boolean = false
) => {
  const date = dateInput instanceof Date ? dateInput : new Date(dateInput);

  if (isNaN(date.getTime())) {
    throw new Error("Invalid date");
  }

  let sliceIdx: number;

  if (fullYear) {
    sliceIdx = 4;
  } else {
    sliceIdx = 2;
  }

  const yy = String(date.getFullYear()).slice(-sliceIdx);
  const mm = String(date.getMonth() + 1).padStart(2, "0");
  const dd = String(date.getDate()).padStart(2, "0");

  return `${yy}${delimiter}${mm}${delimiter}${dd}`;
};
