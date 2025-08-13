// Date 객체 format과 관련한 함수
export const formatDate = (
  dateInput: Date | string | number,
  delimiter: "-" | ".",
  fullYear = false
) => {
  const date = dateInput instanceof Date ? dateInput : new Date(dateInput);

  if (isNaN(date.getTime())) {
    throw new Error("Invalid date");
  }

  const year = String(date.getFullYear()).slice(fullYear ? 0 : -2);
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");

  return [year, month, day].join(delimiter);
};
