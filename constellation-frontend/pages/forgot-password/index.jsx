import Button from "@/components/Button";
import Input from "@/components/Input";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import { useRouter } from "next/router";
import { useState } from "react";
const ForgotPassword = () => {
  const router = useRouter();
  const [form, setForm] = useState({
    username: "",
    newPassword: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch(`${process.env.GATEWAY_URL}/users/${form.username}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ newPassword: form.newPassword }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error();
        }

        alert("Password updated successfully!");
        router.push("/");
      })
      .catch((error) => {
        alert("Failed to update password, user does not exist");
      });
  };
  return (
    <Shell>
      <Paper width="400px" className="mt-12 flex flex-col">
        <p className="text-center font-semibold mt-2 font-sans text-3xl text-gray-800">
          Forgot Password
        </p>
        <form onSubmit={handleSubmit}>
          <Input
            className="mt-14"
            placeholder="Username"
            name="username"
            required
            onChange={handleChange}
          />
          <Input
            placeholder="New Password"
            type="password"
            name="newPassword"
            required
            onChange={handleChange}
          />
          <Button className="mt-12 w-full" type="submit">
            Reset Password
          </Button>
        </form>

        <Button className="mt-1" onClick={() => router.push("/")}>
          Back
        </Button>
      </Paper>
    </Shell>
  );
};

export default ForgotPassword;
