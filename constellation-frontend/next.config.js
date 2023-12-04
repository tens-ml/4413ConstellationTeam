/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  env: {
    GATEWAY_URL: process.env.GATEWAY_URL,
  },
};

module.exports = nextConfig;
