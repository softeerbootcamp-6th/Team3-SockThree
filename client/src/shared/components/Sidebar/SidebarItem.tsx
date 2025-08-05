import { Link } from "react-router";

interface SidebarItemProps {
  item: {
    icon: React.ReactNode;
    label: string;
    route: string;
    isSelected?: boolean;
  };
}

const SidebarItem = ({ item }: SidebarItemProps) => {
  return (
    <Link to={item.route} className="">
      <span className="">{item.icon}</span>
      <span className="">{item.label}</span>
    </Link>
  );
};

export default SidebarItem;
