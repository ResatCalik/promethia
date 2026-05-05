import { useEffect, useState } from "react";
import api from "../api/axios";
import Sidebar from "../components/Sidebar";
import { useAuth } from "../context/AuthContext";

function FollowingPage() {
  const { user } = useAuth();
  const [following, setFollowing] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchFollowing = async () => {
    try {
      setLoading(true);
      setError("");
      const response = await api.get(`/users/${user.userId}/following`);
      setFollowing(response.data.data || []);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load following");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (user?.userId) {
      fetchFollowing();
    }
  }, [user]);

  return (
    <div className="layout">
      <Sidebar />

      <main className="content">
        <h1>Following</h1>

        {loading && <p>Loading following...</p>}
        {error && <p className="error-text">{error}</p>}
        {!loading && !error && following.length === 0 && <p>You are not following anyone yet.</p>}

        <div className="user-list">
          {following.map((item) => (
            <div key={item.userId} className="user-card">
              <h3>{item.username}</h3>
              <p>{item.email}</p>
              {item.bio && <p>{item.bio}</p>}
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}

export default FollowingPage;