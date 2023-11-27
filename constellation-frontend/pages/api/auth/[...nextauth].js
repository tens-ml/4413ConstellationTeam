import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import GithubProvider from "next-auth/providers/github";

const credentialProvider = CredentialsProvider({
  // The name to display on the sign in form (e.g. 'Sign in with...')
  name: "Credentials",
  credentials: {
    username: { label: "Username", type: "text" },
    password: { label: "Password", type: "password" },
  },

  async authorize(credentials, req) {
    // const res = await fetch("/your/endpoint", {
    //   method: "POST",
    //   body: JSON.stringify(credentials),
    //   headers: { "Content-Type": "application/json" },
    // });
    const res = await Promise.resolve({ ok: true });
    const user = { name: "John" };
    // const user = await res.json();

    if (res.ok && user) {
      return user;
    }

    return null;
  },
});

export const authOptions = {
  // Configure one or more authentication providers
  providers: [
    GithubProvider({
      clientId: process.env.GITHUB_ID,
      clientSecret: process.env.GITHUB_SECRET,
    }),
    credentialProvider,
  ],
};

export default NextAuth(authOptions);
