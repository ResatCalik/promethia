import { useEffect, useState } from "react";
import api from "../api/axios";
import Sidebar from "../components/Sidebar";
import PostCard from "../components/PostCard";

function FeedPage() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchFeed = async () => {
    try {
      setLoading(true);
      setError("");
      const response = await api.get("/posts/feed");
      setPosts(response.data.data || []);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load feed");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchFeed();
  }, []);

  return (
    <div className="layout">
      <Sidebar />

      <main className="content">
        <h1>Home Feed</h1>

        {loading && <p>Loading feed...</p>}
        {error && <p className="error-text">{error}</p>}
        {!loading && !error && posts.length === 0 && <p>No posts yet.</p>}

        <div className="post-list">
          {posts.map((post) => (
            <PostCard key={post.id} post={post} />
          ))}
        </div>
      </main>
    </div>
  );
}

export default FeedPage;