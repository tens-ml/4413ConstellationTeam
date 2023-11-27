import Button from "@/components/Button";
import Input from "@/components/Input";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import { useRouter } from "next/router";
const Signup = () => {
  const router = useRouter();
  return (
    <Shell>
      <Paper width="400px" className="mt-32 flex flex-col">
        <p className="text-center font-semibold mt-2 font-sans text-3xl text-gray-800">
          Sign up
        </p>
        <form>
          <Input className="mt-12" placeholder="Username" required />
          <Input placeholder="Email" required />
          <Button className="mt-12 w-full">Create Account</Button>
        </form>

        <Button className="mt-1" onClick={() => router.push("/")}>
          Back
        </Button>
      </Paper>
    </Shell>
  );
};

export default Signup;
