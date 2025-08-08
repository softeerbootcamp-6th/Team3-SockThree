import FunnelForm from "@/domain/instructor/component/funnel/FunnelForm.tsx";
import { FunnelSideBar } from "@/domain/instructor/component/funnel/FunnelSideBar.tsx";
import FunnelHeader from "@/domain/instructor/component/funnel/FunnelHeader.tsx";
import { useFunnelState } from "@/domain/instructor/hook/useFunnelState.ts";

const CreateCoursePage = () => {
  const { funnelState, curStep, stepKeys, handleValidChange, isStepValid } =
    useFunnelState();

  return (
    <div className="flex min-h-screen flex-col pr-[5rem] pl-[10rem]">
      <FunnelHeader currentIndex={curStep} totalSteps={stepKeys.length} />
      <main className="flex flex-row gap-7">
        <FunnelForm
          context={funnelState}
          curStep={curStep}
          stepKeys={stepKeys}
          handleValidChange={handleValidChange}
        />
        <FunnelSideBar
          stepKeys={stepKeys}
          curStep={curStep}
          funnelState={funnelState}
          isStepValid={isStepValid}
        />
      </main>
    </div>
  );
};

export default CreateCoursePage;
