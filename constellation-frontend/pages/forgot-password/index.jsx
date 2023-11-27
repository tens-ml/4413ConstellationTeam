import Button from "@/components/Button";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import { useRouter } from "next/router";

const ForgotPassword = () => {
  const router = useRouter();
  return (
    <Shell>
      <Paper width="400px" className="mt-32 flex flex-col">
        <p className="text-center font-semibold mt-2 font-sans text-3xl text-gray-800">
          Forgot Password
        </p>
        <form>
          <input
            className="mt-12 w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-400 focus:border-transparent"
            placeholder="Username"
            required
          />
          <input
            className="mt-1 w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-400 focus:border-transparent"
            placeholder="New Password"
            required
          />
          <input
            className="mt-1 w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-400 focus:border-transparent"
            placeholder="Reset Code (given on signup)"
            required
            type="number"
          />
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
