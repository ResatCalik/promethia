import { useEffect, useState } from "react";
import api from "../api/axios";
import Sidebar from "../components/Sidebar";
import PostCard from "../components/PostCard";

function MyPostsPage() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchMyPosts = async () => {
    try {
      setLoading(true);
      setError("");
      const response = await api.get("/posts/me");
      setPosts(response.data.data || []);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load my posts");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchMyPosts();
  }, []);

  return (
    <div className="layout">
      <Sidebar />

      <main className="content">
        <h1>My Posts</h1>

        {loading && <p>Loading posts...</p>}
        {error && <p className="error-text">{error}</p>}
        {!loading && !error && posts.length === 0 && <p>You have no posts yet.</p>}

        <div className="post-list">
          {posts.map((post) => (
            <PostCard key={post.id} post={post} />
          ))}
        </div>
      </main>
    </div>
  );
}

export default MyPostsPage;