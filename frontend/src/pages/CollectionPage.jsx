import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import wretch from "wretch";
import LoadingSpinner from "../components/LoadingSpinner";
import PreviewGrid from "../components/PreviewGrid";
import { Container } from "../components/styled";
import basePath from "../utils";

function CollectionPage() {
  const [loading, setLoading] = useState(true);
  const [files, setFiles] = useState([]);
  const { id } = useParams();

  useEffect(() => {
    wretch(`${basePath()}/api/collection/${id}`)
      .get()
      .json((filenames) => {
        setFiles(filenames.map(filename => `${basePath()}/api/collection/${id}/${filename}`));
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <Container>
        <LoadingSpinner />
      </Container>
    );
  }

  return <PreviewGrid files={files} />;
}

export default CollectionPage;
