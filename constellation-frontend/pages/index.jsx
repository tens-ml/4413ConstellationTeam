import Button from "@/components/Button";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import Title from "@/components/Title";
import { getServerSession } from "next-auth/next";
import { signIn, useSession } from "next-auth/react";
import { useRouter } from "next/router";
import { authOptions } from "./api/auth/[...nextauth]";
export default function Home({ data }) {
  const router = useRouter();
  const handleForgotPassword = () => {
    router.push("/forgot-password");
  };

  const { user } = useSession();
  if (user) router.push("/catalog");

  return (
    <Shell>
      <Paper width="600px" className="mt-32">
        <img src="/logo.jpg" width={240} height={240} alt="Logo" />
        <div className="grow flex flex-col ml-8">
          <Title className="text-center mt-8">Welcome</Title>
          <div className="grow flex flex-col justify-center space-y-2">
            <Button onClick={() => router.push("/signup")}>Sign up</Button>
            <Button
              onClick={() => signIn(undefined, { callbackUrl: "/catalog" })}
            >
              Login
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

export async function getServerSideProps(context) {
  const req = await getServerSession(context.req, context.res, authOptions);
  if (req) {
    console.log("request:");
    console.log(JSON.stringify(req));
    // return {
    //   redirect: {
    //     destination: "/catalog",
    //     permanent: false,
    //   },
    // };
  } else {
    console.log("not req");
  }

  return { props: {} };
}
