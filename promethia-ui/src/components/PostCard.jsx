import { Link } from "react-router-dom";

function PostCard({ post }) {
  const firstImage = post.images && post.images.length > 0 ? post.images[0] : null;

  return (
    <div className="post-card">
      {firstImage && (
        <img
          src={firstImage.imageUrl}
          alt={post.title}
          className="post-card-image"
        />
      )}

      <div className="post-card-content">
        <h3>{post.title}</h3>
        <p>{post.summary}</p>

        <div className="post-card-meta">
          <span>By {post.authorUsername}</span>
          <span>{post.likeCount} likes</span>
        </div>

        <Link to={`/posts/${post.id}`} className="detail-link">
          View Details
        </Link>
      </div>
    </div>
  );
}

export default PostCard;