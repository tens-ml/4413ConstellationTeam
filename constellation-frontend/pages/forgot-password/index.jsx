import Button from "@/components/Button";
import Input from "@/components/Input";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import { useRouter } from "next/router";

const ForgotPassword = () => {
  const router = useRouter();
  return (
    <Shell>
      <Paper width="400px" className="mt-12 flex flex-col">
        <p className="text-center font-semibold mt-2 font-sans text-3xl text-gray-800">
          Forgot Password
        </p>
        <form>
          <Input className="mt-14" placeholder="Username" required />
          <Input placeholder="New Password" required />
          <Button className="mt-12 w-full">Reset Password</Button>
        </form>

        <Button className="mt-1" onClick={() => router.push("/")}>
          Back
        </Button>
      </Paper>
    </Shell>
  );
};

export default ForgotPassword;
