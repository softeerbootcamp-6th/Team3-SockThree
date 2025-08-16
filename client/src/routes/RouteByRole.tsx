import type { UserType } from "@/shared/types/auth";
import Error404 from "@/shared/page/error/Error404";
import { useAuth } from "@/shared/hook/useAuth";

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
