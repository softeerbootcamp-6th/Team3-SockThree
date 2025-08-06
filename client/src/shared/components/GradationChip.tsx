interface GradationChipProps {
  children: React.ReactNode;
}

const GradationChip = ({ children }: GradationChipProps) => {
  return (
    <div className="rounded-full border-[.0625rem] border-main-400 bg-gradient-to-br from-[#86DED6]/30 via-[#26CABA]/50 to-[#E5F1F1] px-[var(--spacing-12)] py-[var(--spacing-4)]">
      {children}
    </div>
  );
};

export default GradationChip;
