import { Link } from "react-router";

interface NavbarItemProps {
  icon: React.FC<React.SVGProps<SVGSVGElement>>;
  label: string;
  routePath: string;
  isSelected?: boolean;
  onClick?: () => void;
}

const NavbarItem = ({
  icon: Icon,
  label,
  routePath,
  isSelected,
  onClick,
}: NavbarItemProps) => {
  return (
    <li className="h-[7.75rem] w-[5.9375rem] cursor-pointer" onClick={onClick}>
      <Link
        to={routePath}
        className="flex h-full flex-col items-center justify-start"
      >
        <div
          className={`mb-[.625rem] flex h-[5rem] w-[5rem] items-center justify-center rounded-[1.875rem] transition-all duration-500 ${
            isSelected ? "bg-white" : "bg-transparent"
          }`}
        >
          <Icon
            className={`h-[2rem] transition-colors duration-300 ${
              isSelected ? "text-black" : "text-gray-500"
            }`}
          />
        </div>

        <span
          className={`typo-label-0 transition-colors duration-300 ${
            isSelected ? "text-black" : "text-gray-500"
          }`}
        >
          {label}
        </span>
      </Link>
    </li>
  );
};

export default NavbarItem;
