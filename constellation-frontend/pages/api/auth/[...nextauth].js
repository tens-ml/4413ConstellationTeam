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
    console.log("fetching: ", `${process.env.GATEWAY_URL}/users/login`);
    const res = await fetch(`${process.env.GATEWAY_URL}/users/login`, {
      method: "POST",
      body: JSON.stringify(credentials),
      headers: { "Content-Type": "application/json" },
    });

    if (res.ok) {
      console.log("fetch was okay");
      return { name: "danny" };
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
  session: { jwt: true },
  callbacks: {
    async jwt({ token, account }) {
      // Persist the OAuth access_token and or the user id to the token right after signin
      if (account) {
        token.accessToken = account.access_token;
      }
      return token;
    },
    async session({ session, token }) {
      session.accessToken = token.accessToken;
      return session;
    },
  },
};

export default NextAuth(authOptions);
