import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axios";
import Sidebar from "../components/Sidebar";

function PostDetailPage() {
  const { id } = useParams();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchPost = async () => {
    try {
      setLoading(true);
      setError("");
      const response = await api.get(`/posts/${id}`);
      setPost(response.data.data);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load post");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPost();
  }, [id]);

  return (
    <div className="layout">
      <Sidebar />

      <main className="content">
        {loading && <p>Loading post...</p>}
        {error && <p className="error-text">{error}</p>}

        {!loading && !error && post && (
          <div className="post-detail-card">
            <h1>{post.title}</h1>
            <p className="post-detail-meta">
              By {post.authorUsername} · {post.likeCount} likes
            </p>

            {post.images && post.images.length > 0 && (
              <div className="post-detail-images">
                {post.images.map((image) => (
                  <img
                    key={image.id ?? image.imageUrl}
                    src={image.imageUrl}
                    alt={post.title}
                    className="post-detail-image"
                  />
                ))}
              </div>
            )}

            <h3>Summary</h3>
            <p>{post.summary}</p>

            <h3>Details</h3>
            <p className="post-detail-content">{post.detailContent}</p>
          </div>
        )}
      </main>
    </div>
  );
}

export default PostDetailPage;