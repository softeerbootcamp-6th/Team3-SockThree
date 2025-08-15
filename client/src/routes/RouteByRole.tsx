import type { UserType } from "@/shared/types/auth";
import { useAuth } from "@/shared/context/AuthContext";

export const RouteByRole = ({
  components,
}: {
  components: { [key in UserType]?: React.FC };
}) => {
  const { userType } = useAuth();
  const Component = components[userType];

  if (!Component) {
    return <div>404 not found\</div>;
  }
  return <Component />;
};
