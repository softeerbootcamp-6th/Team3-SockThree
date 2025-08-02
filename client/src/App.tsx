import { Button } from "@/components/common/button";

function App() {
  return (
    <div className="flex min-h-svh flex-col items-center justify-center gap-2">
      <Button>이 강좌 신청하기</Button>
      <Button variant={"outline"}>다음 기수 신청하기</Button>
      <Button disabled>신청 완료</Button>
      <Button variant={"outline"} size={"lgFlexible"} textSize={"title2"}>
        전체 강좌 둘러보기
      </Button>
      <Button size={"md"}>강좌 신청하기</Button>
      <Button variant={"hoverUp"} size={"sm"}>+ 강의 업로드 하기</Button>
      <Button variant={"ghost"} size={"xs"}>
        로그아웃
      </Button>
    </div>
  );
}

export default App;
