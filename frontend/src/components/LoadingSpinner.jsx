import blocksWave from "../assets/blocks-wave.svg";

function LoadingSpinner() {
  return (
    <img
      src={blocksWave}
      alt="Loading..."
      style={{ width: "45px", height: "45px" }}
    />
  );
}

export default LoadingSpinner;
