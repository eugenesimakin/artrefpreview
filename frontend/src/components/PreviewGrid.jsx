import { useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { SmallContainer } from "./styled";
import DropImagesArea from "./DropImagesArea";
import LoadingSpinner from "./LoadingSpinner";
import basePath from "../utils";

const DropImagesContainer = styled.details`
  text-align: center;
  padding-top: 2rem;
  padding-left: 1rem;
  padding-right: 1rem;
`;
const DropImagesSummary = styled.summary`
  cursor: pointer;
  margin-bottom: 0.8rem;
`;
const ImagesContainer = styled.div`
  padding-top: 2rem;
  padding-left: 1rem;
  padding-right: 1rem;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  grid-gap: 10px;
  grid-auto-rows: min-max(160px, auto);
  grid-auto-flow: dense;
`;
const Image = styled.img`
  width: 100%;
  min-width: 100px;
  min-height: 200px;
  background: #eee;
`;

function PreviewGrid({ files }) {
  const hasFiles = files.length > 0;
  const [uploading, setUploading] = useState(false);
  const { id } = useParams();

  const handleDrop = async (files) => {
    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      formData.append("file", file, file.name);
    }

    try {
      await fetch(`${basePath()}/api/collection/${id}/upload`, {
        method: "POST",
        body: formData,
      });
    } finally {
      location.reload();
    }
  };

  return (
    <>
      {hasFiles && (
        <ImagesContainer>
          {files.map((file) => {
            return <Image key={file} src={file} />;
          })}
        </ImagesContainer>
      )}
      {uploading && (
        <SmallContainer>
          <LoadingSpinner />
        </SmallContainer>
      )}
      {!uploading && (
        <DropImagesContainer>
          <DropImagesSummary>Click to show drop area</DropImagesSummary>
          <DropImagesArea
            onDrop={handleDrop}
            onStartResizing={() => setUploading(true)}
          />
        </DropImagesContainer>
      )}
    </>
  );
}

export default PreviewGrid;
