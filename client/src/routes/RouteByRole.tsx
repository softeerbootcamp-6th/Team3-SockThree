import type { UserType } from "@/shared/types/auth";
import { useAuth } from "@/shared/context/AuthContext";
import Error404 from "@/shared/page/error/Error404";

export const RouteByRole = ({
  components,
}: {
  components: { [key in UserType]?: React.FC };
}) => {
  const { userType } = useAuth();
  const Component = components[userType];

  if (!Component) {
    return <Error404 />;
  }
  return <Component />;
};
