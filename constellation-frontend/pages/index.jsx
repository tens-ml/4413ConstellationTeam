import Button from "@/components/Button";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import Title from "@/components/Title";
import { signIn, useSession } from "next-auth/react";
import { useRouter } from "next/router";

export default function Home({ data }) {
  const router = useRouter();
  const handleForgotPassword = () => {
    router.push("/forgot-password");
  };

  const session = useSession();
  const user = session?.user;
  console.log("session:", session);
  //if (user) router.push("/catalog");
  if (user) console.log("authed");
  return (
    <Shell>
      <Paper width="600px" className="mt-32">
        <img src="/logo.jpg" width={240} height={240} alt="Logo" />
        <div className="grow flex flex-col ml-8">
          <Title className="text-center mt-8">Welcome</Title>
          <div className="grow flex flex-col justify-center space-y-2">
            <Button onClick={() => router.push("/signup")}>Sign up</Button>
            <Button
              onClick={() => signIn("github", { callbackUrl: "/catalog" })}
            >
              Login with Github
            </Button>
            <Button
              onClick={() => signIn("Credentials", { callbackUrl: "/catalog" })}
            >
              Login with Credentials
            </Button>
            <a
              className="cursor-pointer text-right"
              onClick={() => router.push("/forgot-password")}
            >
              Forgot password?
            </a>
          </div>
        </div>
      </Paper>
    </Shell>
  );
}
