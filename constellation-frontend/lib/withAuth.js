import { signIn, useSession } from "next-auth/react";
import { useRouter } from "next/router";
import { useEffect } from "react";

function withAuth(WrappedComponent) {
  return function WithAuth(props) {
    const { user } = useSession();
    const router = useRouter();

    useEffect(() => {
      // If no session exists and we're not already on the sign-in page, redirect to it
      if (!user) {
        signIn();
      }
    }, [user, router]);

    return <WrappedComponent {...props} />;
  };
}
