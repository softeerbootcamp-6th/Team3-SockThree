import FunnelForm from "@/domain/instructor/component/funnel/FunnelForm";
import { FunnelSideBar } from "@/domain/instructor/component/funnel/FunnelSideBar";
import FunnelHeader from "@/domain/instructor/component/funnel/FunnelHeader";
import { useFunnelState } from "@/domain/instructor/hook/useFunnelState";

const CreateCoursePage = () => {
  const { funnelState, curStep, stepKeys, handleValidChange, isStepValid } =
    useFunnelState();

  return (
    <div className="w-100vw flex min-h-screen flex-col pr-[5rem] pl-[10rem]">
      <FunnelHeader currentIndex={curStep} totalSteps={stepKeys.length} />
      <main className="flex flex-1 gap-[2rem]">
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
