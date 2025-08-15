interface InfoChipProps {
  text: string;
}
const InfoChip = ({ text }: InfoChipProps) => {
  return (
    <div className="flex h-fit w-fit items-center gap-2.5 rounded-full bg-gradient-to-r from-[#A0EACF] to-[#7DA7F9] px-2 py-2">
      <span className="flex h-6 w-6 items-center justify-center rounded-full bg-white text-[#0066FF]">
        !
      </span>
      <span className="typo-label-3 text-white">{text}</span>
    </div>
  );
};

export default InfoChip;
