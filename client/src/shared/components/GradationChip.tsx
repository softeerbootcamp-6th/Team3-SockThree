interface GradationChipProps {
  children: React.ReactNode;
}

const GradationChip = ({ children }: GradationChipProps) => {
  return (
    <div className="flex items-center rounded-10 border-[.0625rem] border-main-400 bg-[linear-gradient(322deg,rgba(134,222,214,0.15)_0%,rgba(38,202,186,0.25)_46.64%,rgba(229,241,241,0.50)_97.99%)] px-[0.4rem] py-[0.4rem]">
      {children}
    </div>
  );
};

export default GradationChip;
