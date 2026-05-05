import { useEffect, useState } from "react";
import api from "../api/axios";
import Sidebar from "../components/Sidebar";
import { useAuth } from "../context/AuthContext";

function FollowersPage() {
  const { user } = useAuth();
  const [followers, setFollowers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchFollowers = async () => {
    try {
      setLoading(true);
      setError("");
      const response = await api.get(`/users/${user.userId}/followers`);
      setFollowers(response.data.data || []);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load followers");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (user?.userId) {
      fetchFollowers();
    }
  }, [user]);

  return (
    <div className="layout">
      <Sidebar />

      <main className="content">
        <h1>Followers</h1>

        {loading && <p>Loading followers...</p>}
        {error && <p className="error-text">{error}</p>}
        {!loading && !error && followers.length === 0 && <p>No followers yet.</p>}

        <div className="user-list">
          {followers.map((item) => (
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

export default FollowersPage;