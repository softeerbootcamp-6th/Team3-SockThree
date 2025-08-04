interface SidebarItemProps {
  item: {
    icon: React.ReactNode;
    label: string;
    route?: string;
    isSelected?: boolean;
  };
  onClick?: () => void;
}

const SidebarItem = ({ item, onClick }: SidebarItemProps) => {
  return (
    <button className="" onClick={onClick}>
      <span className="">{item.icon}</span>
      <span className="">{item.label}</span>
    </button>
  );
};

export default SidebarItem;
