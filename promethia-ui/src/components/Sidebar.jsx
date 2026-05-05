import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Sidebar() {
  const { logout, user } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <aside className="sidebar">
      <h2 className="sidebar-logo">Promethia</h2>

      <p className="sidebar-user">@{user?.username}</p>

      <nav className="sidebar-nav">
        <Link to="/">Home Feed</Link>
        <Link to="/my-posts">My Posts</Link>
        <Link to="/followers">Followers</Link>
        <Link to="/following">Following</Link>
      </nav>

      <button className="logout-button" onClick={handleLogout}>
        Logout
      </button>
    </aside>
  );
}

export default Sidebar;