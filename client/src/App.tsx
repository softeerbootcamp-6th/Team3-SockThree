import { Button } from "@/components/common/button";

function App() {
  return (
    <div className="flex min-h-svh flex-col items-center justify-center gap-2">
      <Button >이 강좌 합류하기</Button>
      <Button variant={"outline"}>이 강사로 선택</Button>
      <Button disabled>신청 완료</Button>
      <Button variant={"outline"} size={"flexible"} textSize={"title2"}>
        안녕
      </Button>
    </div>
  );
}

export default App;
