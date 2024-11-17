/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  async rewrites() {
    return [
      {
        source: "/api/:path*", // Next.js 프록시 엔드포인트
        destination: "http://localhost:8080/:path*", // Spring Boot 서버
      },
    ];
  },
};

export default nextConfig;
